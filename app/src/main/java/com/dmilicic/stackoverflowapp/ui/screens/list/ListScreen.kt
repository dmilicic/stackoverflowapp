package com.dmilicic.stackoverflowapp.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dmilicic.stackoverflowapp.R
import com.dmilicic.stackoverflowapp.models.BadgeCounts
import com.dmilicic.stackoverflowapp.models.UserModel
import com.dmilicic.stackoverflowapp.ui.theme.StackOverflowAppTheme

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    onClick: (Int) -> Unit = {},
) {
    val users = viewModel.uiState.collectAsStateWithLifecycle().value.users
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
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable { onClickItem(user.userId) }
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(64.dp),
                model = user.profileImage ?: android.R.drawable.sym_def_app_icon,
                contentDescription = "User profile picture",
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = user.displayName ?: "Unknown user",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Button(
                        modifier = Modifier.height(32.dp),
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
                            Text(text = "Following", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        } else {
                            Text(text = "Follow")
                        }
                    }

                }
                Text(
                    text = buildAnnotatedString {
                        append("Reputation: ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                            append("${user.reputation}")
                        }
                    },
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