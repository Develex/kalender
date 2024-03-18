package com.develex.baseapp.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.develex.baseapp.DarkTheme
import com.develex.baseapp.LocalTheme
import com.develex.baseapp.MainViewModel
import com.example.compose.AppTheme

@Composable
fun Page2Screen(navController: NavController, vm: MainViewModel) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
//                Content page starts here
                Column(
                    modifier = Modifier.fillMaxSize().padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                            .clip(MaterialTheme.shapes.large)
                    ) {
                        Text(
                            "Page2 Screen",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            }
        }