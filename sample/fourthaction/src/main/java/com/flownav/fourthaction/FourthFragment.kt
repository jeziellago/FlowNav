package com.flownav.fourthaction

import androidx.fragment.app.Fragment
import com.flownav.annotation.EntryFragmentFlowNav
import com.flownav.navigation.NavigationFragmentRoutes

@EntryFragmentFlowNav(NavigationFragmentRoutes.FourthFragment.ACTION_NAME, NavigationFragmentRoutes.FourthFragment.FRAGMENT_ID)
class FourthFragment: Fragment(R.layout.fragment_fourth)