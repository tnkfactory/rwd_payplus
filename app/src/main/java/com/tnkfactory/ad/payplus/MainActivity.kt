package com.tnkfactory.ad.payplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.tnkfactory.ad.Logger
import com.tnkfactory.ad.TnkOfferwall
import com.tnkfactory.ad.TnkSession
import com.tnkfactory.ad.rwdplus.kakao.TnkRwdPlus
import com.tnkfactory.ad.rwdplus.kakao.scene.OverlayButton
import com.tnkfactory.ad.rwdplus.kakao.scene.RwdPlusLoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TNK 오퍼월 초기화
        val tnkRwdPlus = TnkRwdPlus(this).apply {
            // coppa 설정 (미국 어린이 온라인 개인정보 보호법)https://www.ftc.gov/business-guidance/privacy-security/childrens-privacy
            setCOPPA(false)
            // scheme 설정 androidManifest.xml의 RwdPlusLoginActivity의 scheme과 동일해야 합니다.
            // 예시 <data android:host="payplus" android:scheme="tnkrwd100014" />
            setScheme("tnkrwd100014")
        }

        // 오퍼월 화면 출력하는 버튼 설정
        findViewById<View>(R.id.tv_test).setOnClickListener {
            // 오퍼월 화면을 출력합니다.
            tnkRwdPlus.showOfferwall(this@MainActivity)
        }
    }
}