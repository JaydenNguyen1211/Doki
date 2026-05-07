package mazentas.doki.videoplayer.ui.composables

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mazentas.doki.videoplayer.ui.preview.DayNightPreview
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.theme.DokiPlayerTheme
import kotlin.math.log


private const val TAG = "PermissionCheckView"
@Composable
fun PermissionGeneralView(
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Log.d(TAG, "PermissioGeneralView: open")
        Image(
            painter = painterResource(R.drawable.img_logo),
            contentDescription = "App Icon",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.permission_not_granted),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.permission_general_request),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = stringResource(R.string.allow))
        }
    }
}


@Composable
fun PermissionDetailView(
    text: String,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Log.d(TAG, "PermissionDetailView: open")
        Text(
            text = stringResource(id = R.string.permission_not_granted),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + context.packageName)
                    context.startActivity(this)
                }
            },
        ) {
            Text(text = stringResource(R.string.open_settings))
        }
    }
}

@DayNightPreview
@Composable
fun PermissionDetailViewPreview() {
    DokiPlayerTheme {
        Surface {
            PermissionDetailView(
                text = stringResource(
                    id = R.string.permission_settings,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
            )
        }
    }
}
