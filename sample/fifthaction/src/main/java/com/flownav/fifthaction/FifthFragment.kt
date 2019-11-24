package com.flownav.fifthaction

import androidx.fragment.app.Fragment
import com.flownav.annotation.EntryFragmentFlowNav
import com.flownav.navigation.NavigationFragmentRoutes

@EntryFragmentFlowNav(NavigationFragmentRoutes.FifthFragment.ACTION_NAME_FIFTH, NavigationFragmentRoutes.FifthFragment.FRAGMENT_ID_FIFTH)
class FifthFragment : Fragment(R.layout.fragment_fifth)
