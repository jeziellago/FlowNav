package com.flownav.fifthaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationFragmentRoutes.FifthFragment.ACTION_NAME_FIFTH
import com.flownav.navigation.NavigationFragmentRoutes.FifthFragment.ACTION_NAME_FIFTH_CHILD
import com.flownav.navigation.NavigationFragmentRoutes.FifthFragment.FRAGMENT_ID_FIFTH
import com.flownav.router.startFragment
import kotlinx.android.synthetic.main.fragment_fifth.*

@EntryFlowNav(ACTION_NAME_FIFTH, FRAGMENT_ID_FIFTH)
class FifthFragment : Fragment(R.layout.fragment_fifth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCallFragmentChild.setOnClickListener {
            startFragment(R.id.containerFeatureFifth, ACTION_NAME_FIFTH_CHILD)
        }
    }
}

@EntryFlowNav(ACTION_NAME_FIFTH_CHILD)
class FifthFragmentChild : Fragment(R.layout.fragment_fifth_child)
