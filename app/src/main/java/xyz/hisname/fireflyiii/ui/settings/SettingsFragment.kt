package xyz.hisname.fireflyiii.ui.settings

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.core.app.ActivityCompat
import xyz.hisname.fireflyiii.R
import androidx.fragment.app.commit
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.*
import kotlinx.android.synthetic.main.activity_base.*
import xyz.hisname.fireflyiii.data.local.pref.AppPref
import xyz.hisname.fireflyiii.util.biometric.KeyguardUtil
import xyz.hisname.languagepack.LanguageChanger


class SettingsFragment: BaseSettings() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.user_settings)
        setAccountSection()
        setTransactionSection()
        setLanguagePref()
        setNightModeSection()
        setPinCode()
        setThumbnail()
        setTutorial()
        setDeveloperOption()
        deleteItems()
    }

    private fun setLanguagePref(){
        val languagePref = findPreference<ListPreference>("language_pref") as ListPreference
        languagePref.value = AppPref(sharedPref).languagePref
        languagePref.setOnPreferenceChangeListener { _, newValue ->
            AppPref(sharedPref).languagePref = newValue.toString()
            LanguageChanger.init(requireContext(), AppPref(sharedPref).languagePref)
            ActivityCompat.recreate(requireActivity())
            true
        }
        languagePref.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_language
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun setAccountSection(){
        val accountOptions = findPreference<Preference>("account_options") as Preference
        accountOptions.setOnPreferenceClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                replace(R.id.fragment_container, SettingsAccountFragment())
            }
            true
        }
        accountOptions.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_account_circle
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun setTransactionSection(){
        val transactionSettings = findPreference<Preference>("transaction_settings") as Preference
        transactionSettings.setOnPreferenceClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                replace(R.id.fragment_container, TransactionSettings())
            }
            true
        }

        transactionSettings.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_notifications
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun setNightModeSection(){
        val nightModePref = findPreference<CheckBoxPreference>("night_mode") as CheckBoxPreference
        nightModePref.setOnPreferenceChangeListener { preference, newValue ->
            val nightMode = newValue as Boolean
            AppPref(sharedPref).nightModeEnabled = nightMode
            true
        }
        nightModePref.icon = IconicsDrawable(requireContext()).apply {
            icon = FontAwesome.Icon.faw_moon
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun setThumbnail(){
        val thumbnailPref = findPreference<CheckBoxPreference>("currencyThumbnail") as CheckBoxPreference
        thumbnailPref.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_attach_money
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun setTutorial(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val tutorialSetting = findPreference<Preference>("tutorial_setting") as Preference
            tutorialSetting.isVisible = true
            tutorialSetting.icon = IconicsDrawable(requireContext()).apply {
                icon = FontAwesome.Icon.faw_university
                sizeDp = 24
                colorRes = setIconColor()
            }
            tutorialSetting.setOnPreferenceClickListener {
                requireContext().deleteSharedPreferences("PrefShowCaseView")
                ActivityCompat.recreate(requireActivity())
                true
            }
        }
    }

    private fun setDeveloperOption(){
        val developerSettings = findPreference<Preference>("developer_options") as Preference
        developerSettings.setOnPreferenceClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                replace(R.id.fragment_container, DeveloperSettings())
            }
            true
        }
        developerSettings.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_developer_mode
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.activity_toolbar?.title = resources.getString(R.string.settings)
    }

    override fun onResume() {
        super.onResume()
        activity?.activity_toolbar?.title = resources.getString(R.string.settings)
    }

    override fun handleBack() {
        parentFragmentManager.popBackStack()
    }

    private fun setIconColor(): Int{
        return if(globalViewModel.isDark){
            R.color.md_white_1000
        } else {
            R.color.md_black_1000
        }
    }

    private fun setPinCode(){
        val keyguardPref = findPreference<Preference>("keyguard") as Preference
        if(!KeyguardUtil(requireActivity()).isDeviceKeyguardEnabled() || BiometricManager.from(requireContext()).canAuthenticate() != BIOMETRIC_SUCCESS){
            keyguardPref.isSelectable = false
            keyguardPref.summary = "Please enable pin / password / biometrics in your device settings"
        }
        keyguardPref.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_lock
            sizeDp = 24
            colorRes = setIconColor()
        }
    }

    private fun deleteItems(){
        val deleteData = findPreference<Preference>("delete_data") as Preference
        deleteData.icon = IconicsDrawable(requireContext()).apply {
            icon = GoogleMaterial.Icon.gmd_delete_forever
            sizeDp = 24
            colorRes = setIconColor()
        }
        deleteData.setOnPreferenceClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                replace(R.id.fragment_container, DeleteItemsFragment())
            }
            true
        }
    }
}