package com.example.subway.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.subway.OnboardingSetUp;
import com.example.subway.R;

public class Onboarding extends AppCompatActivity {

    private Button onboardingSkipButton;
    private Button onboardingNextButton;
    private OnboardingSetUp onboardingSetUp = new OnboardingSetUp();
    private LinearLayout layoutOnboardingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingSetUp.onboardingAdapter);
        onboardingViewPager.setUserInputEnabled(false);

        layoutOnboardingIndicator = findViewById(R.id.onboardingIndicatorsDots);
        OnboardingIndicatorsSetUp();
        OnboardingIndicatorsStatus(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                OnboardingIndicatorsStatus(position);
            }
        });


        this.onboardingSkipButton = findViewById(R.id.onboardingSkipButton);
        this.onboardingSkipButton.setOnClickListener(v -> {
            startActivity(new Intent(Onboarding.this, StartupScreen.class));
            finish();
        });

        this.onboardingNextButton = findViewById(R.id.onboardingNextButton);
        this.onboardingNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingSetUp.onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
                } else {
                    startActivity(new Intent(Onboarding.this, StartupScreen.class));
                    finish();
                }
                if (onboardingViewPager.getCurrentItem() + 1 == onboardingSetUp.onboardingAdapter.getItemCount()){
                    onboardingSkipButton.setVisibility(View.GONE);
                }
            }
        });
    }


    private void OnboardingIndicatorsSetUp(){
        ImageView[] onboardingIndicators = new ImageView[onboardingSetUp.onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int currentIndicatorIndex = 0; currentIndicatorIndex < onboardingIndicators.length; currentIndicatorIndex++){
            onboardingIndicators[currentIndicatorIndex] = new ImageView(getApplicationContext());
            onboardingIndicators[currentIndicatorIndex].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            onboardingIndicators[currentIndicatorIndex].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(onboardingIndicators[currentIndicatorIndex]);
        }
    }
    private void OnboardingIndicatorsStatus(int index){
        for (int screenIndex = 0; screenIndex < layoutOnboardingIndicator.getChildCount(); screenIndex++){
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(screenIndex);
            if (screenIndex == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboarding_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboarding_indicator_inactive
                ));
            }
        }
    }

}
