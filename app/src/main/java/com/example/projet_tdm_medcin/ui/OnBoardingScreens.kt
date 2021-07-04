package com.example.projet_tdm_medcin.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.ui.adapters.OnBoardingAdapter


class OnBoardingScreens : AppCompatActivity() {
    private var onboardingAdapter: OnBoardingAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (restorePrefData()) {
            val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
            var connected=pref.getBoolean("connected", false)

            if (!connected) {
                val loginActivity = Intent(applicationContext, AuthActivity::class.java)
                startActivity(loginActivity)
                finish()
            } else {
                val mainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainActivity)
                finish()

            }
        }

        setContentView(R.layout.activity_on_boarding_screens)

        var buttonOnboardingAction = findViewById<Button>(R.id.buttonOnBoardingAction)
        setOnboardingItem()
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingAdapter

        setOnboadingIndicator()
        setCurrentOnboardingIndicators(0)
        onboardingViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)
            }
        })
        buttonOnboardingAction.setOnClickListener(){
            if (onboardingViewPager.currentItem + 1 < onboardingAdapter!!.itemCount) {
                onboardingViewPager.currentItem = onboardingViewPager.currentItem + 1
            } else {
                startActivity(Intent(applicationContext, AuthActivity::class.java))
                savePrefsData()
                finish()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentOnboardingIndicators(index: Int) {
        var layoutOnboardingIndicator = findViewById<LinearLayout>(R.id.layoutOnboardingIndicators)
        var buttonOnboardingAction = findViewById<Button>(R.id.buttonOnBoardingAction)

        val childCount: Int = layoutOnboardingIndicator.getChildCount()
        for (i in 0 until childCount) {
            val imageView = layoutOnboardingIndicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboarding_indicator_inactive
                    )
                )
            }
        }
        if (index == onboardingAdapter?.getItemCount()?.minus(1)){
            buttonOnboardingAction.setText("Commencer")
        }else {
            buttonOnboardingAction.setText("Suivant")
        }
    }



    private fun setOnboardingItem() {
        val onBoardingItems: MutableList<OnBoardingItem> = mutableListOf()
        val item1 = OnBoardingItem()
        item1.title="Consultez vous traitements"
        item1.description="Vous pouvez consulter vos traitements via l'application!"
        item1.image=R.drawable.on_boarding_screen1

        val item2 = OnBoardingItem()
        item2.title="Contactez un medecin"
        item2.description="Vous pouvez contacter un medecin directemeent via l'application!"
        item2.image=R.drawable.on_boarding_screen2

        val item3 = OnBoardingItem()

        item3.title="Prennez un rendez-vous"
        item3.description="Vous pouvez reserver directement via l'application!"
        item3.image=R.drawable.on_boarding_screen3

        val item4 = OnBoardingItem()
        item4.title="Demmander conseil"
        item4.description="Vous pouvez contacter un medecin et demander conseil!"
        item4.image=R.drawable.on_boarding_screen4

        onBoardingItems.add(item1);
        onBoardingItems.add(item2);
        onBoardingItems.add(item3);
        onBoardingItems.add(item4);

        onboardingAdapter = OnBoardingAdapter(onBoardingItems);
    }

    private fun setOnboadingIndicator() {
        var layoutOnboardingIndicator = findViewById<LinearLayout>(R.id.layoutOnboardingIndicators)
        val indicators: Array<ImageView?> = arrayOfNulls<ImageView>(onboardingAdapter!!.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.onboarding_indicator_inactive
                )
            )
            indicators[i]?.setLayoutParams(layoutParams)
            layoutOnboardingIndicator!!.addView(indicators[i])
        }
    }
    private fun restorePrefData(): Boolean {
        val pref =
            applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isOpnend", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isOpnend", true)
        editor.commit()
    }


    
    
}