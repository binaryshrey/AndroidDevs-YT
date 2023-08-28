package dev.shreyansh.androiddevsyt.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dev.shreyansh.androiddevsyt.R
import dev.shreyansh.androiddevsyt.databinding.FragmentHomeBinding
import dev.shreyansh.androiddevsyt.utils.AndroidDevsRecyclerAdapter
import dev.shreyansh.androiddevsyt.utils.ClickListener
import dev.shreyansh.androiddevsyt.viewmodel.AndroidDevsViewModel
import dev.shreyansh.androiddevsyt.viewmodel.AndroidDevsViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var androidDevsRecyclerAdapter: AndroidDevsRecyclerAdapter
    private val viewModel: AndroidDevsViewModel by activityViewModels {
        AndroidDevsViewModelFactory(requireNotNull(this.activity).application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        showSupportActionBar()
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.green_700)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        setupObservers()


        return binding.root
    }

    private fun showSupportActionBar() {
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(),R.color.green_700)))

    }

    private fun setupObservers() {
        viewModel.playlist.observe(viewLifecycleOwner, Observer {
            it?.let {
                androidDevsRecyclerAdapter.submitList(it.toMutableList())
            }
        })
    }

    private fun setupRecyclerView() {
        androidDevsRecyclerAdapter = AndroidDevsRecyclerAdapter(ClickListener { it ->
            Toast.makeText(context, "${it.title}", Toast.LENGTH_SHORT).show()
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        })
        binding.listRV.adapter = androidDevsRecyclerAdapter

    }


    private fun signOutFlow() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googlesSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        googlesSignInClient.signOut()
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToIntroFragment())


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logOut -> {
                        signOutFlow()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}