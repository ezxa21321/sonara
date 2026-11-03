package com.sonora.music.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sonora.music.PlayerViewModel
import com.sonora.music.data.DemoData
import com.sonora.music.ui.components.BottomBar
import com.sonora.music.ui.components.MiniPlayer
import com.sonora.music.ui.navigation.Routes
import com.sonora.music.ui.screens.ArtistScreen
import com.sonora.music.ui.screens.CollectionScreen
import com.sonora.music.ui.screens.HomeScreen
import com.sonora.music.ui.screens.LibraryScreen
import com.sonora.music.ui.screens.PlayerScreen
import com.sonora.music.ui.screens.SearchScreen
import com.sonora.music.ui.theme.Ink

@Composable
fun SonoraApp(vm: PlayerViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val playback by vm.playback.collectAsStateWithLifecycle()
    val position by vm.positionMs.collectAsStateWithLifecycle()
    val toastMessage by vm.toast.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val isPlayer = route == Routes.PLAYER
    val showChrome = route != null && !isPlayer

    val currentTrack = DemoData.track(playback.currentId)
    val progress = if (playback.durationMs > 0L) {
        position.toFloat() / playback.durationMs.toFloat()
    } else {
        0f
    }

    val selectedTab = when {
        route?.startsWith("search") == true -> 1
        route?.startsWith("library") == true -> 2
        else -> 0
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(toastMessage) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            vm.clearToast()
        }
    }

    fun openCollection(id: String) {
        navController.navigate(Routes.collection(id))
    }

    fun openArtist(id: String) {
        navController.navigate(Routes.artist(id))
    }

    fun openTab(index: Int) {
        val target = when (index) {
            1 -> Routes.SEARCH
            2 -> Routes.LIBRARY
            else -> Routes.HOME
        }
        navController.navigate(target) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun openPlayer() {
        navController.navigate(Routes.PLAYER) { launchSingleTop = true }
    }

    Scaffold(
        containerColor = Ink,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showChrome) {
                Column {
                    AnimatedVisibility(
                        visible = currentTrack != null,
                        enter = slideInVertically { it } + fadeIn(),
                        exit = slideOutVertically { it } + fadeOut()
                    ) {
                        currentTrack?.let { track ->
                            MiniPlayer(
                                track = track,
                                isPlaying = playback.isPlaying,
                                progress = progress,
                                onPlayPause = vm::togglePlayPause,
                                onNext = vm::next,
                                onOpen = { openPlayer() }
                            )
                        }
                    }
                    BottomBar(
                        selected = selectedTab,
                        onSelect = { index -> openTab(index) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(180)) },
            exitTransition = { fadeOut(animationSpec = tween(160)) }
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    vm = vm,
                    onOpenCollection = { id -> openCollection(id) },
                    onOpenArtist = { id -> openArtist(id) },
                    onOpenLibrary = { openTab(2) },
                    onShowToast = vm::showToast
                )
            }

            composable(Routes.SEARCH) {
                SearchScreen(
                    vm = vm,
                    onOpenCollection = { id -> openCollection(id) },
                    onOpenArtist = { id -> openArtist(id) }
                )
            }

            composable(Routes.LIBRARY) {
                LibraryScreen(
                    vm = vm,
                    onOpenCollection = { id -> openCollection(id) },
                    onOpenArtist = { id -> openArtist(id) },
                    onShowToast = vm::showToast
                )
            }

            composable(
                route = Routes.COLLECTION,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                CollectionScreen(
                    vm = vm,
                    collectionId = entry.arguments?.getString("id").orEmpty(),
                    onBack = { navController.popBackStack() },
                    onShowToast = vm::showToast
                )
            }

            composable(
                route = Routes.ARTIST,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                ArtistScreen(
                    vm = vm,
                    artistId = entry.arguments?.getString("id").orEmpty(),
                    onBack = { navController.popBackStack() },
                    onOpenCollection = { id -> openCollection(id) },
                    onShowToast = vm::showToast
                )
            }

            composable(
                route = Routes.PLAYER,
                enterTransition = {
                    slideInVertically(animationSpec = tween(320), initialOffsetY = { it })
                },
                exitTransition = {
                    slideOutVertically(animationSpec = tween(260), targetOffsetY = { it })
                },
                popEnterTransition = {
                    slideInVertically(animationSpec = tween(320), initialOffsetY = { it })
                },
                popExitTransition = {
                    slideOutVertically(animationSpec = tween(260), targetOffsetY = { it })
                }
            ) {
                PlayerScreen(
                    vm = vm,
                    onBack = { navController.popBackStack() },
                    onOpenArtist = { id -> openArtist(id) },
                    onShowToast = vm::showToast
                )
            }
        }
    }
}
