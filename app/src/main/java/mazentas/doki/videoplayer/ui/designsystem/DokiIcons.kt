package mazentas.doki.videoplayer.ui.designsystem

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.CompareArrows
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.filled.GridOff
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AppSettingsAlt
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.BookmarkAdded
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CenterFocusStrong
import androidx.compose.material.icons.outlined.CenterFocusWeak
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ClosedCaption
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeveloperBoard
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.DoubleArrow
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FastForward
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.FlipToBack
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.FolderOff
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Headset
import androidx.compose.material.icons.outlined.HeadsetOff
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MovieFilter
import androidx.compose.material.icons.outlined.MusicVideo
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhotoSizeSelectLarge
import androidx.compose.material.icons.outlined.PictureInPictureAlt
import androidx.compose.material.icons.outlined.Pinch
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Replay10
import androidx.compose.material.icons.outlined.Replay5
import androidx.compose.material.icons.outlined.ResetTv
import androidx.compose.material.icons.outlined.ScreenRotationAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SmartButton
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material.icons.outlined.Swipe
import androidx.compose.material.icons.outlined.SwipeVertical
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object DokiIcons {
    val Appearance = Icons.Outlined.BrightnessMedium
    val ArrowBack = Icons.AutoMirrored.Outlined.ArrowBack
    val ArrowDownward = Icons.Outlined.ArrowDownward
    val ArrowUpward = Icons.Outlined.ArrowUpward
    val Audio = Icons.Outlined.MusicVideo
    val Background = Icons.Outlined.FlipToBack
    val Bold = Icons.Outlined.FormatBold
    val Brightness = Icons.Outlined.BrightnessHigh
    val Calendar = Icons.Outlined.DateRange
    val Caption = Icons.Outlined.ClosedCaption
    val Check = Icons.Outlined.Check
    val BookMark = Icons.Outlined.BookmarkAdded
    val CheckBox = Icons.Outlined.CheckCircle
    val CheckBoxOutline = Icons.Outlined.RadioButtonUnchecked
    val Contrast = Icons.Outlined.Contrast
    val DarkMode = Icons.Outlined.DarkMode
    val DashBoard = Icons.Outlined.Dashboard
    val Decoder = Icons.Outlined.MovieFilter
    val Delete = Icons.Outlined.Delete
    val DoubleTap = Icons.Outlined.DoubleArrow
    val Edit = Icons.Outlined.Edit
    val Fast = Icons.Outlined.FastForward
    val FileOpen = Icons.Outlined.FileOpen
    val Focus = Icons.Outlined.CenterFocusWeak
    val Folder = Icons.Outlined.Folder
    val FolderOff = Icons.Outlined.FolderOff
    val Font = Icons.Outlined.FontDownload
    val FontSize = Icons.Outlined.FormatSize
    val Headset = Icons.Outlined.Headset
    val HeadsetOff = Icons.Outlined.HeadsetOff
    val Info = Icons.Outlined.Group
    val Language = Icons.Outlined.Language
    val Length = Icons.Outlined.Timer
    val Link = Icons.Outlined.Link
    val Location = Icons.Outlined.LocationOn
    val Movie = Icons.Outlined.Movie
    val Pip = Icons.Outlined.PictureInPictureAlt
    val Pinch = Icons.Outlined.Pinch
    val Play = Icons.Outlined.PlayArrow
    val Player = Icons.Outlined.SmartDisplay
    val Priority = Icons.Outlined.PriorityHigh
    val Replay = Icons.Outlined.Replay5
    val Resume = Icons.Outlined.ResetTv
    val Rotation = Icons.Outlined.ScreenRotationAlt
    val Selection = Icons.Outlined.DoneAll
    val Settings = Icons.Outlined.Settings
    val Share = Icons.Outlined.Share
    val SmartButton = Icons.Outlined.SmartButton
    val Style = Icons.Outlined.Style
    val Subtitle = Icons.Outlined.Subtitles
    val Size = Icons.Outlined.PhotoSizeSelectLarge
    val Speed = Icons.Outlined.Speed
    val SwipeHorizontal = Icons.Outlined.Swipe
    val SwipeVertical = Icons.Outlined.SwipeVertical
    val Tap = Icons.Outlined.TouchApp
    val Timer = Icons.Outlined.Timer
    val Title = Icons.Outlined.TextFields
    val Update = Icons.Outlined.Update
    val Video = Icons.Outlined.Movie
    val VolumeUp = Icons.AutoMirrored.Outlined.VolumeUp
    val ButtonsPosition = Icons.Outlined.AppSettingsAlt
    val Close = Icons.Outlined.Close
    val History = Icons.Outlined.History
    val Option = Icons.Filled.MoreVert
    val Setting = Icons.Filled.Settings
    val List = Icons.Filled.GridOff
    val Grid = Icons.Filled.GridOn
    val Sort = Icons.Outlined.SortByAlpha
}

@Composable
fun DokiIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colorsz: IconButtonColors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
    content: @Composable () -> Unit
) {
    IconButton(onClick = onClick, colors =  colorsz, content =  content)
}
