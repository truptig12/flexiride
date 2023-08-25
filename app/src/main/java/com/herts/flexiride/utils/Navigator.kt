package com.herts.flexiride.utils

import android.content.Context
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.data.response.SignInResponse
import com.herts.flexiride.presentation.AddAvailabilityActivity
import com.herts.flexiride.presentation.AddNewCarActivity
import com.herts.flexiride.presentation.AddPackageActivity
import com.herts.flexiride.presentation.AddPhotosActivity
import com.herts.flexiride.presentation.CarDetailsActivity
import com.herts.flexiride.presentation.RegisterActivity
import com.herts.flexiride.presentation.HomeActivity
import com.herts.flexiride.presentation.LoginActivity
import com.herts.flexiride.presentation.OwnerDashboard
import com.herts.flexiride.presentation.WelcomeActivity


object Navigator {

    fun navigateToWelcomeActivity(context: Context) {
        context.startActivity(WelcomeActivity.getCallingIntent(context))
    }

    fun navigateToRegisterActivity(context: Context) {
        context.startActivity(RegisterActivity.getCallingIntent(context))
    }

    fun navigateToHomeActivity(context: Context) {
        context.startActivity(HomeActivity.getCallingIntent(context))
    }

    fun navigateToLoginActivity(context: Context) {
        context.startActivity(LoginActivity.getCallingIntent(context))
    }

   /* fun navigateToOwnerDashboardActivity(context: Context, signInResponse: SignInResponse) {
        context.startActivity(OwnerDashboard.getCallingIntent(context, signInResponse))
    }
*/
    fun navigateToOwnerDashboardActivity(context: Context) {
        context.startActivity(OwnerDashboard.getCallingIntent(context))
    }

    fun navigateToAddNewCarActivity(context: Context) {
        context.startActivity(AddNewCarActivity.getCallingIntent(context))
    }

    fun navigateToAddPhotosActivity(context: Context, id: Int, city: String) {
        context.startActivity(AddPhotosActivity.getCallingIntent(context, id, city))
    }

    fun navigateToAddPackageActivity(context: Context, id: Int) {
        context.startActivity(AddPackageActivity.getCallingIntent(context, id))
    }

    fun navigateToAddAvailabilityActivity(context: Context, id: Int, city: String) {
        context.startActivity(AddAvailabilityActivity.getCallingIntent(context, id, city))
    }

    fun navigateToCarDetailsActivity(context: Context, car: CarList) {
        context.startActivity(CarDetailsActivity.getCallingIntent(context, car))
    }
}