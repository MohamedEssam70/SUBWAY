package com.example.subway;

import java.util.ArrayList;
import java.util.List;

public class OnboardingSetUp {
    public OnboardingAdapter onboardingAdapter;
    OnboardingSetUp() {
        List <OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem onboardingFirstScreen = new OnboardingItem();
        onboardingFirstScreen.setImage(R.drawable.intro_first_img);
        onboardingFirstScreen.setTitle(R.string.onboardingFirstTitle);
        onboardingFirstScreen.setDescription(R.string.onboardingFirstDescription);
        onboardingItems.add(onboardingFirstScreen);

        OnboardingItem onboardingSecondScreen = new OnboardingItem();
        onboardingSecondScreen.setImage(R.drawable.intro_second_img);
        onboardingSecondScreen.setTitle(R.string.onboardingSecondTitle);
        onboardingSecondScreen.setDescription(R.string.onboardingSecondDescription);
        onboardingItems.add(onboardingSecondScreen);

        OnboardingItem onboardingThirdScreen = new OnboardingItem();
        onboardingThirdScreen.setImage(R.drawable.intro_third_img);
        onboardingThirdScreen.setTitle(R.string.onboardingThirdTitle);
        onboardingThirdScreen.setDescription(R.string.onboardingThirdDescription);
        onboardingItems.add(onboardingThirdScreen);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
}
