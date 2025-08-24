package com.example.quranapp.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.R
import com.example.quranapp.location.OrientationHandler
import com.example.quranapp.location.QiblaDirectionHandler
import com.example.quranapp.screens.helpercomposable.CameraPermissionHandler
import com.example.quranapp.screens.helpercomposable.CameraPreview
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun QiblaScreen(){

    val context = LocalContext.current

    val qiblaDirectionHandler = remember { QiblaDirectionHandler(context) }
    val orientationHandler = remember { OrientationHandler(context) }

    val azimuth = orientationHandler.azimuth.floatValue
    val direction = qiblaDirectionHandler.direction.value

    LaunchedEffect(Unit) {
        orientationHandler.start()
        qiblaDirectionHandler.getLastLocation()
    }

    CameraPermissionHandler(
        onCameraPermissionGranted = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CameraPreview(Modifier.fillMaxSize())
                Icon(
                    painter = painterResource(R.drawable.compass),
                    contentDescription = "Compass",
                    tint = Color.Black,
                    modifier = Modifier.size(300.dp).align(Alignment.Center)
                        .graphicsLayer(
                            rotationZ = -((azimuth + 360) % 360)
                        )
                )
            }

        },
        onCameraPermissionDenied = {
            Box (
                modifier = Modifier.fillMaxSize()
            ){
                Icon(
                    painter = painterResource(R.drawable.compass),
                    contentDescription = "Compass",
                    tint = Color.Black,
                    modifier = Modifier.size(300.dp).align(Alignment.Center)
                        .graphicsLayer(
                            rotationZ = -((azimuth + 360) % 360),

                        )
                )
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.TopStart)
                ){
                    Text(
                        text = stringResource(R.string.give_camera_access),
                        modifier = Modifier.padding(8.dp)
                            .widthIn(max = 200.dp),
                        fontSize = 16.sp,
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick ={
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppPurple
                        ),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(stringResource(R.string.give_access))
                    }
                }
            }
        },
        onLocationPermissionGranted = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.compass_arrow),
                    contentDescription = "Compass Arrow",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(200.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(
                            rotationZ = ((direction?.toFloat() ?: 0f) - azimuth + 360) % 360
                        )
                )
            }
        },
        onLocationPermissionDenied = {
            Column (
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = stringResource(R.string.location_access_denied),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp
                    )
                Button(
                    onClick = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppPurple
                    )
                ) {
                    Text(stringResource(R.string.give_access))
                }
            }
        }
    )
}