package ru.skillbranch.devintensive.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository
import ru.skillbranch.devintensive.utils.Utils

class ProfileViewModel: ViewModel() {

    private val repository: PreferencesRepository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val isRepositoryError = MutableLiveData<Boolean>()


    init {
        profileData.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
        isRepositoryError.value = false
    }

    fun getProfileData(): LiveData<Profile> = profileData
    fun getTheme() : LiveData<Int> = appTheme
        fun getIsRepositoryError(): LiveData<Boolean> = isRepositoryError

    fun saveProfileData(profile: Profile) {
        val otherProfile = if (isRepositoryError.value!!)
            profile.copy(repository = "")
        else
            profile
        repository.saveProfile(otherProfile)
        profileData.value = otherProfile
    }

    fun switchTheme() {
        if (appTheme.value == AppCompatDelegate.MODE_NIGHT_YES){
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        }
        else{
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun onRepositoryTextChanged(s: String){
        isRepositoryError.value = !Utils.validateRepository(s)
    }
}