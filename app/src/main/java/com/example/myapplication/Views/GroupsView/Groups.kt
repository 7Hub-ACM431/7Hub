package com.example.myapplication.Views.GroupsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.Utilities.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onGroupClick: (Group) -> Unit = {}
) {
    val groups by viewModel.groups.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Constants.hubWhite) // Arka plan rengini Constants'tan alıyor
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Groups", color = Constants.hubGray)
                }
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Constants.hubWhite
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Constants.hubGreen // Renk Constants'tan geliyor
            )
        )

        // New Group Button
        Button(
            onClick = { /* Yeni grup oluşturma */ },
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .fillMaxWidth(0.5f), // Genişliği ayarlamak için fillMaxWidth kullanabilirsiniz
            colors = ButtonDefaults.buttonColors(containerColor = Constants.hubGreen),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = "Add",
                    tint = Constants.hubWhite,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "New Group",
                    color = Constants.hubWhite
                )
            }
        }

        // Groups List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(groups) { group ->
                GroupCard(
                    group = group,
                    onClick = { onGroupClick(group) }
                )
            }
        }
    }
}

@Composable
fun GroupCard(
    group: Group,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(369.dp)
            .height(95.dp) // Yükseklik ayarlanabilir
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Constants.hubWhite)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Group Icon
            Surface(
                modifier = Modifier.size(50.dp), // İkon boyutu
                shape = RoundedCornerShape(8.dp),
                color = Constants.hubBabyBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = null,
                    tint = Constants.hubWhite,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Group Name
            Text(
                text = group.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                color = Constants.hubWhite // Yazı rengi
            )

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Group",
                tint = Constants.hubGreen
            )
        }
    }
}

@Preview
@Composable
fun PreviewGroupsScreen() {
    GroupsScreen()
}