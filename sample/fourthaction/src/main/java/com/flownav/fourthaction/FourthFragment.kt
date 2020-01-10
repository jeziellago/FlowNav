package com.flownav.fourthaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationFragmentRouter
import com.flownav.navigation.NavigationFragmentRoutes
import kotlinx.android.synthetic.main.fragment_fourth.*

@EntryFlowNav(NavigationFragmentRoutes.FourthFragment.ACTION_NAME, NavigationFragmentRoutes.FourthFragment.FRAGMENT_ID)
class FourthFragment : Fragment(R.layout.fragment_fourth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGoToFifth.setOnClickListener {
            NavigationFragmentRouter.navigateToFifth(this)
        }
    }
}
