package mazentas.doki.videoplayer.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mazentas.doki.videoplayer.model.ApplicationPreferences
import mazentas.doki.videoplayer.model.MediaViewMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokiCenterAlignedTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        contentPadding = PaddingValues(horizontal = 8.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokiTopAppBar(
    title: String,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = fontWeight,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokiBottomBar(applicationPreferences: ApplicationPreferences, updatePreferences: (ApplicationPreferences) -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }
    var preferences by remember {  mutableStateOf(applicationPreferences) }
    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == VIDEO_TAB_INDEX,
            onClick = {
                selectedIndex = VIDEO_TAB_INDEX
                preferences = preferences.copy(mediaViewMode = MediaViewMode.VIDEOS)
                updatePreferences(preferences)
            },
            icon = {
                Icon(
                    Icons.Filled.Videocam,
                    "Video",
                    tint = if (selectedIndex == VIDEO_TAB_INDEX) MaterialTheme.colorScheme.primary else Color(0xFF49454F)
                )
            }
        )

        NavigationBarItem(
            selected = selectedIndex == FOLDER_TAB_INDEX,
            onClick = {
                selectedIndex = FOLDER_TAB_INDEX
                preferences = preferences.copy(mediaViewMode = MediaViewMode.FOLDERS)
                updatePreferences(preferences)
            },
            icon = {
                Icon(
                    Icons.Filled.Folder,
                    "Folder",
                    tint = if (selectedIndex == FOLDER_TAB_INDEX) MaterialTheme.colorScheme.primary else Color(0xFF49454F)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DokiPlayerMainTopAppBarPreview() {
    DokiCenterAlignedTopAppBar(
        title = "Next Player",
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Settings",
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More",
                )
            }
        },
    )
}

const val VIDEO_TAB_INDEX = 0
const val FOLDER_TAB_INDEX = 1
