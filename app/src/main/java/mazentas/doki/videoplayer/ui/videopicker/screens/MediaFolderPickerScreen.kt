package mazentas.doki.videoplayer.ui.videopicker.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.common.storagePermission
import mazentas.doki.videoplayer.media.services.MediaService
import mazentas.doki.videoplayer.model.MediaLayoutMode
import mazentas.doki.videoplayer.model.Video
import mazentas.doki.videoplayer.ui.base.DataState
import mazentas.doki.videoplayer.ui.components.DokiBottomBar
import mazentas.doki.videoplayer.ui.components.DokiTopAppBar
import mazentas.doki.videoplayer.ui.composables.PermissionMissingView
import mazentas.doki.videoplayer.ui.designsystem.DokiIconButton
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons
import mazentas.doki.videoplayer.ui.extensions.copy
import mazentas.doki.videoplayer.ui.videopicker.composables.CenterCircularProgressBar
import mazentas.doki.videoplayer.ui.videopicker.composables.MediaView
import mazentas.doki.videoplayer.ui.videopicker.composables.QuickSettingsDialog
import mazentas.doki.videoplayer.ui.videopicker.composables.RenameDialog
import mazentas.doki.videoplayer.ui.videopicker.composables.VideoInfoDialog
import mazentas.doki.videoplayer.ui.videopicker.rememberSelectionManager
import mazentas.doki.videoplayer.ui.videopicker.screens.mediapicker.MediaPickerUiEvent
import mazentas.doki.videoplayer.ui.videopicker.screens.mediapicker.MediaPickerUiState


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalPermissionsApi::class)
@Composable
internal fun MediaFolderPickerScreen(
    uiState: MediaPickerUiState,
    onNavigateUp: () -> Unit = {},
    onPlayVideos: (List<Uri>) -> Unit = {},
    onFolderClick: (String) -> Unit = {},
    onEvent: (MediaPickerUiEvent) -> Unit = {},
) {
    val selectionManager = rememberSelectionManager()
    val permissionState = rememberPermissionState(permission = storagePermission)
    val lazyGridState = rememberLazyGridState()
    val selectVideoFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { it?.let { onPlayVideos(listOf(it)) } },
    )

    var isFabExpanded by rememberSaveable { mutableStateOf(false) }
    var showQuickSettingsDialog by rememberSaveable { mutableStateOf(false) }
    var showUrlDialog by rememberSaveable { mutableStateOf(false) }
    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var showRenameActionFor: Video? by rememberSaveable { mutableStateOf(null) }
    var showInfoActionFor: Video? by rememberSaveable { mutableStateOf(null) }
    var showDeleteVideosConfirmation by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DokiTopAppBar(
                title = (uiState.folderName ?: stringResource(R.string.app_name)).takeIf { !selectionManager.isInSelectionMode } ?: "",
                fontWeight = FontWeight.Bold.takeIf { uiState.folderName == null },
                navigationIcon = {
                    DokiIconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = DokiIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { scaffoldPadding ->
        when (uiState.mediaDataState) {
            is DataState.Error -> {
            }

            is DataState.Loading -> {
                CenterCircularProgressBar(modifier = Modifier.padding(scaffoldPadding))
            }

            is DataState.Success -> {
                PullToRefreshBox(
                    modifier = Modifier.padding(top = scaffoldPadding.calculateTopPadding()),
                    isRefreshing = uiState.refreshing,
                    onRefresh = { onEvent(MediaPickerUiEvent.Refresh) },
                ) {
                    PermissionMissingView(
                        isGranted = permissionState.status.isGranted,
                        showRationale = permissionState.status.shouldShowRationale,
                        permission = permissionState.permission,
                        launchPermissionRequest = { permissionState.launchPermissionRequest() },
                    ) {
                        MediaView(
                            rootFolder = uiState.mediaDataState.value,
                            preferences = uiState.preferences,
                            onFolderClick = onFolderClick,
                            onVideoClick = { onPlayVideos(listOf(it)) },
                            selectionManager = selectionManager,
                            lazyGridState = lazyGridState,
                            contentPadding = scaffoldPadding.copy(top = 0.dp),
                            onVideoLoaded = { onEvent(MediaPickerUiEvent.AddToSync(it)) },
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(lazyGridState.isScrollInProgress) {
        if (isFabExpanded && lazyGridState.isScrollInProgress) {
            isFabExpanded = false
        }
    }

    LaunchedEffect(selectionManager.isInSelectionMode) {
        if (selectionManager.isInSelectionMode) {
            isFabExpanded = false
        }
    }

    BackHandler(enabled = isFabExpanded) {
        isFabExpanded = false
    }

    BackHandler(enabled = selectionManager.isInSelectionMode) {
        selectionManager.clearSelection()
    }
    


    showRenameActionFor?.let { video ->
        RenameDialog(
            name = video.displayName,
            onDismiss = { showRenameActionFor = null },
            onDone = {
                onEvent(MediaPickerUiEvent.RenameVideo(video.uriString.toUri(), it))
                showRenameActionFor = null
                selectionManager.clearSelection()
            },
        )
    }

    showInfoActionFor?.let { video ->
        VideoInfoDialog(
            video = video,
            onDismiss = { showInfoActionFor = null },
        )
    }

}