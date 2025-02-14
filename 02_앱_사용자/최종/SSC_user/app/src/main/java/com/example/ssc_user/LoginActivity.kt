package com.example.ssc_user

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG: String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.login_id)
        val password = findViewById<EditText>(R.id.login_pw)

        textView3.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
            finish()
        }

        //로그인
        login_btn.setOnClickListener {
            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
//                            updateUI(user)

                            val nextIntent = Intent(this, FirstActivity::class.java)
                            val inputEmail = email.text.toString()
                            val inputPassword = password.text.toString()
                            nextIntent.putExtra("email", inputEmail)
                            nextIntent.putExtra("password", inputPassword)
                            startActivity(nextIntent)


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                        // ...
                    }
            }
        }

        //xml에 적힌 이름 바로 적어 줘도 되네... 굿
//        xml_btn_main_googleLogin.setOnClickListener {
//            val intent = Intent(this, JoinActivity::class.java)
//            startActivity(intent)
////            overridePendingTransition(R.anim.slide_in, R.anim.slide_out) //이건 안 먹힘???
//        }

//        //로그아웃
//        xml_btn_main_googleLogin.setOnClickListener {
//            auth.signOut()
//            //로그인 활성화 - 이걸 더 효율적으로 하는 방법이 있을것 같은데 일일히 적어 줘야 해?
//            tv_message.setText("로그인이 필요합니다..")
//            xml_btn_main_googleLogin.isEnabled = false
//            login_btn.isEnabled = true
////            bt_create.isEnabled = true
//        }
    }


    override fun onResume() {
        super.onResume()
        val currentUser = auth?.currentUser
        updateUI(currentUser)

    }

    override fun onStart() {
        super.onStart()
        //앱 시작 단계에서 사용자가 현재 로그인 되어 있는지 확인하고 처리 해 준다.
        val currentUser = auth?.currentUser
        updateUI(currentUser) //이건 원하는대로 사용자 설정해 주는 부분인듯 하다.
    }

    fun updateUI(cUser : FirebaseUser? = null){
        if(cUser != null) {
            auth.signOut()
            tv_message.setText("로그인 되었습니다.")
            //로그인 버튼과 기타 등등을 사용할 수 없게 함(일괄 묶어서 처리 하는 방법?)
            login_btn.isEnabled = true
//            xml_btn_main_googleLogin.isEnabled = true
//            bt_logout.isEnabled = true
        } else {
            tv_message.setText("로그인이 필요합니다..")
//            xml_btn_main_googleLogin.isEnabled = false
        }
//        et_email0.setText("")
//        et_password0.setText("")
//        hideKeyboard(et_email0)
        //Toast.makeText(this, "유저: "+cUser.toString(), Toast.LENGTH_SHORT).show()
    }

//    private fun hideKeyboard(view: View) {
//        view?.apply {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//    }
}
