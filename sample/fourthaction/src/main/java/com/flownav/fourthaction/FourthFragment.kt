package com.flownav.fourthaction

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationFragmentRouter
import com.flownav.navigation.NavigationFragmentRoutes

@EntryFlowNav(NavigationFragmentRoutes.FourthFragment.ACTION_NAME, NavigationFragmentRoutes.FourthFragment.FRAGMENT_ID)
class FourthFragment : Fragment(R.layout.fragment_fourth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonGoToFifth).setOnClickListener {
            NavigationFragmentRouter.navigateToFifth(this)
        }
    }
}
