package com.example.uts_alat_pertanian

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private val profilePrefs: SharedPreferences by lazy {
        getSharedPreferences("profile_prefs", MODE_PRIVATE)
    }

    private val prefsListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "name" || key == "email" || key == "avatar") {
                updateDrawerHeader()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar as ActionBar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Drawer & nav views
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        val headerView = navView.getHeaderView(0)
        ViewCompat.setOnApplyWindowInsetsListener(headerView) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val extraTop = (16 * resources.displayMetrics.density).toInt() // +16dp
            v.setPadding(v.paddingLeft, topInset + extraTop, v.paddingRight, v.paddingBottom)
            insets
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.catalogueFragment,
                R.id.cartFragment,
                R.id.orderFragment,
                R.id.profileFragment
            ),
            drawerLayout
        )

        // Wire toolbar + drawer + navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        // Drawer menu mengikuti navController
        navView.setupWithNavController(navController)

        val rootGraph = navController.graph
        fun defaultTopLevelOptions() = navOptions {
            launchSingleTop = true
            restoreState = true
            popUpTo(rootGraph.startDestinationId) {
                saveState = true
                inclusive = false
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    val opts = navOptions {
                        launchSingleTop = true
                        restoreState = false
                        popUpTo(rootGraph.id) { inclusive = true }
                    }
                    navController.navigate(R.id.homeFragment, null, opts)
                    true
                }
                R.id.catalogueFragment -> {
                    navController.navigate(R.id.catalogueFragment, null, defaultTopLevelOptions())
                    true
                }
                R.id.orderFragment -> {
                    navController.navigate(R.id.orderFragment, null, defaultTopLevelOptions())
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment, null, defaultTopLevelOptions())
                    true
                }
                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment, null, defaultTopLevelOptions())
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.menu.findItem(destination.id)?.isChecked = true
            toolbar.navigationIcon?.setTint(
                ContextCompat.getColor(this, android.R.color.white)
            )
        }

        updateDrawerHeader()

        headerView.setOnClickListener {
            navController.navigate(R.id.profileFragment)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onStart() {
        super.onStart()
        profilePrefs.registerOnSharedPreferenceChangeListener(prefsListener)
    }

    override fun onStop() {
        super.onStop()
        profilePrefs.unregisterOnSharedPreferenceChangeListener(prefsListener)
    }

    private fun updateDrawerHeader() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)

        val iv = header.findViewById<ImageView>(R.id.header_avatar)
        val tvName = header.findViewById<TextView>(R.id.header_title)
        val tvEmail = header.findViewById<TextView>(R.id.header_subtitle)

        val name = profilePrefs.getString("name", "") ?: ""
        val email = profilePrefs.getString("email", "") ?: ""
        val avatar = profilePrefs.getString("avatar", "") ?: ""

        tvName.text = if (name.isBlank()) "User Name" else name
        tvEmail.text = if (email.isBlank()) "user@email.com" else email

        val src = if (avatar.isBlank()) R.mipmap.ic_launcher_round else avatar
        Glide.with(this)
            .load(src)
            .circleCrop()
            .into(iv)
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                navController.navigate(R.id.cartFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}