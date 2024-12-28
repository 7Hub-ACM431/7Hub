package com.example.myapplication.Views.ClubsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.Utilities.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsScreen(
    viewModel: ClubsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onClubClick: (ClubModel) -> Unit = {}
) {
    val clubs by viewModel.clubs.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constants.hubWhite)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Clubs",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 110.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFF3F3F3)
            )
        )

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(
                text = "My Clubs",
                selected = selectedTab == ClubTab.MY_CLUBS,
                onClick = { viewModel.onTabSelected(ClubTab.MY_CLUBS) }
            )
            TabButton(
                text = "All Clubs",
                selected = selectedTab == ClubTab.ALL_CLUBS,
                onClick = { viewModel.onTabSelected(ClubTab.ALL_CLUBS) }
            )
        }

        // Clubs List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(clubs) { club ->
                ClubCard(club = club, onClick = { onClubClick(club) })
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(46.dp)
            .width(196.dp)
            .padding(horizontal = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF3F3F3)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            color = if (selected) Constants.hubGreen else Constants.hubGreen,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ClubCard(
    club: ClubModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(438.dp)
            .height(95.dp)
            .padding(5.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Constants.hubWhite
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Club Icon/Badge
            Surface(
                color = Constants.hubBabyBlue,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(40.dp)
            ) {
                // Burada club.clubIcon'u kullanarak bir Icon ekleyebilirsiniz
            }

            // Club Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = club.clubName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Club",
                tint = Constants.hubGreen
            )
        }
    }
}

@Preview
@Composable
fun ClubsScreenPreview() {
    ClubsScreen()
} 