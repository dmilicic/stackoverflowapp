package com.dmilicic.stackoverflowapp.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmilicic.stackoverflowapp.models.BadgeCounts
import com.dmilicic.stackoverflowapp.models.UserModel
import com.dmilicic.stackoverflowapp.ui.theme.StackOverflowAppTheme

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    onClick: (Int) -> Unit = {},
    users: List<UserModel> = emptyList(),
) {
    ListContent(
        modifier = Modifier.fillMaxSize(),
        users = users,
        onClickItem = onClick,
    )
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    users: List<UserModel> = emptyList(),
    onClickItem: (Int) -> Unit = {}
) {
    if (users.isEmpty()) {
        Text(
            text = "No users yet",
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
        return
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = users,
            key = { it.userId },
        ) { user ->
            UserRow(user = user, onClickItem = onClickItem, isFollowing = true)
        }
    }
}

@Composable
private fun UserRow(
    user: UserModel,
    isFollowing: Boolean = false,
    onClickItem: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(user.userId) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            modifier = Modifier
                .clickable { onClickItem(user.userId) }
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = user.displayName ?: "Unknown user",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Reputation: ${user.reputation}",
                style = MaterialTheme.typography.bodyMedium,
            )
            user.location?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            user.badgeCounts?.let {
                Text(
                    text = "Badges: ${it.gold} gold, ${it.silver} silver, ${it.bronze} bronze",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Button(
            modifier = Modifier.padding(top = 8.dp),
            colors = if (isFollowing) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
            } else {
                ButtonDefaults.buttonColors()
            },
            onClick = { /* TODO: Implement follow functionality */ }
        ) {
            if (isFollowing) {
                Text(text = "Following")
            } else {
                Text(text = "Follow")
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    StackOverflowAppTheme {
        ListContent(
            users = listOf(
                UserModel(
                    accountId = 123,
                    userId = 1,
                    displayName = "John Doe",
                    reputation = 1234,
                    location = "New York, USA",
                    badgeCounts = BadgeCounts(gold = 5, silver = 10, bronze = 15)
                ),
                UserModel(
                    accountId = 456,
                    userId = 2,
                    displayName = "Jane Smith",
                    reputation = 5678,
                    location = "London, UK",
                    badgeCounts = BadgeCounts(gold = 10, silver = 20, bronze = 30)
                )
            )
        )
    }
}