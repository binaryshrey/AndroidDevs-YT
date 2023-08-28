package dev.shreyansh.androiddevsyt.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dev.shreyansh.androiddevsyt.R
import dev.shreyansh.androiddevsyt.databinding.FragmentIntroBinding
import dev.shreyansh.androiddevsyt.utils.NetworkConnection
import dev.shreyansh.androiddevsyt.viewmodel.AndroidDevsViewModel
import dev.shreyansh.androiddevsyt.viewmodel.AndroidDevsViewModelFactory
import timber.log.Timber

class IntroFragment : Fragment() {

    private var isConnected = false
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentIntroBinding
    private lateinit var networkConnection: NetworkConnection
    private lateinit var googleSignInClient: GoogleSignInClient
    private val viewModel: AndroidDevsViewModel by activityViewModels {
        AndroidDevsViewModelFactory(requireNotNull(this.activity).application)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        hideSupportActionBar()
        initFirebaseApp()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)
        Timber.i("IntroFragment inflated")

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupObservers()
        return binding.root
    }




    private fun setupObservers() {
        networkConnection = NetworkConnection(requireNotNull(this.activity).application)
        networkConnection.observe(viewLifecycleOwner, Observer { connected ->
            isConnected = connected
        })

        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { hasLoggedIn ->
            if (hasLoggedIn) {
                if(isConnected){
                    binding.loadingProgress.visibility = View.VISIBLE
                    signInFlow()
                }
                else{
                    Toast.makeText(context,"NO INTERNET CONNECTION!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }




    private fun initFirebaseApp(){
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity().applicationContext, gso)
    }




    private fun hideSupportActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }




    private fun signInFlow() {
        val signInIntent = googleSignInClient.signInIntent
        startForResult.launch(signInIntent)
    }




    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    if (account != null) {
                        firebaseAuthWithGoogle(account)
                    }
                } else {
                    viewModel.onLoginCancel()
                    Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    Timber.e(task.exception.toString())
                }
            }
            if (result.resultCode == Activity.RESULT_CANCELED) {
                viewModel.onLoginCancel()
                binding.loadingProgress.visibility = View.GONE
            }
        }




    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                binding.loadingProgress.visibility = View.GONE
                findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
                viewModel.onLoginComplete()
                Timber.i("Login Success!")
            } else {
                viewModel.onLoginCancel()
                binding.loadingProgress.visibility = View.GONE
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                Timber.e(it.exception.toString())
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
        }
    }

}