package com.example.quranapp.screens.helpercomposable

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CameraPermissionHandler(
    onCameraPermissionGranted: @Composable () -> Unit,
    onCameraPermissionDenied: @Composable () -> Unit,
    onLocationPermissionGranted: @Composable () -> Unit,
    onLocationPermissionDenied: @Composable () -> Unit
) {

    val context = LocalContext.current
    var hasCameraPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var hasAccessFinePermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var hasAccessCoarsePermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsMap ->
            permissionsMap.forEach { (permission, granted) ->
                when(permission){
                    android.Manifest.permission.CAMERA -> hasCameraPermission.value = granted
                    android.Manifest.permission.ACCESS_FINE_LOCATION -> hasAccessFinePermission.value = granted
                    android.Manifest.permission.ACCESS_COARSE_LOCATION -> hasAccessCoarsePermission.value = granted
                }
            }
            println("precise: $hasAccessFinePermission, coarse: $hasAccessCoarsePermission")
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    Box(modifier = Modifier.fillMaxSize()){
        if (hasAccessFinePermission.value && hasAccessCoarsePermission.value){
            if (hasCameraPermission.value){
                onCameraPermissionGranted()
            }else{
                onCameraPermissionDenied()
            }
            onLocationPermissionGranted()
        }else{
            onLocationPermissionDenied()
        }
    }
}