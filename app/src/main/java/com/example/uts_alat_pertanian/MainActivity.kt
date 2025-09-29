package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout   // ⬅️ dipakai buat openDrawer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)   // ⬅️ simpan referensi drawer
        val navView: NavigationView = findViewById(R.id.nav_view)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        // Top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.catalogueFragment,
                R.id.orderFragment,
                R.id.profileFragment,
                R.id.cartFragment
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // ⛔️ JANGAN gunakan binding otomatis
        // bottomNav.setupWithNavController(navController)

        val rootGraph = navController.graph

        // Opsi untuk tab selain Home (jaga state & backstack rapi)
        fun defaultTopLevelOptions() = navOptions {
            launchSingleTop = true
            restoreState = true
            popUpTo(rootGraph.startDestinationId) {
                saveState = true
                inclusive = false
            }
        }

        // ✅ Listener custom bottom nav
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // ⬅️ HOME: kosongkan SEMUA backstack lalu ke Home
                R.id.homeFragment -> {
                    val opts = navOptions {
                        launchSingleTop = true
                        restoreState = false
                        popUpTo(rootGraph.id) { inclusive = true }
                    }
                    navController.navigate(R.id.homeFragment, null, opts)
                    true
                }
                // 🗂 Tab lain
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
                else -> false
            }
        }

        // Sinkronkan highlight bottom nav saat destination berubah
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.menu.findItem(destination.id)?.isChecked = true
        }
    }

    // Dipanggil dari Fragment untuk membuka drawer lewat tombol 3 garis di header
    fun openDrawer() {
        drawerLayout.open() // kalau error, ganti ke: drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                Log.d("MAIN", "Cart button clicked")
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
