package com.dmilicic.stackoverflowapp.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dmilicic.stackoverflowapp.models.BadgeCounts
import com.dmilicic.stackoverflowapp.models.UserModel
import com.dmilicic.stackoverflowapp.ui.theme.StackOverflowAppTheme

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    onClick: (Int) -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    ListContent(
        modifier = Modifier.fillMaxSize(),
        isLoading = uiState.isLoading,
        users = uiState.users,
        followedUsers = uiState.followedUsers,
        onClickItem = onClick,
        onClickFollow = viewModel::onClickFollow,
    )
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    users: List<UserModel> = emptyList(),
    followedUsers: List<Int> = emptyList(),
    onClickItem: (Int) -> Unit = {},
    onClickFollow: (Int) -> Unit = {},
) {
    if (isLoading) {
        ListLoadingState(modifier = modifier)
        return
    }

    if (users.isEmpty()) {
        EmptyContent(modifier = modifier)
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
            UserRow(
                user = user,
                onClickItem = onClickItem,
                isFollowing = followedUsers.contains(user.userId),
                onClickFollow = { onClickFollow(user.userId) }
            )
        }
    }
}

@Composable
private fun UserRow(
    user: UserModel,
    isFollowing: Boolean = false,
    onClickItem: (Int) -> Unit,
    onClickFollow: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
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
                    .size(64.dp)
                    .clip(CircleShape),
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
                        modifier = Modifier.height(40.dp),
                        colors = if (isFollowing) {
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                            )
                        } else {
                            ButtonDefaults.buttonColors()
                        },
                        onClick = onClickFollow,
                    ) {
                        if (isFollowing) {
                            Text(text = "Following")
                        } else {
                            Text(text = "Follow")
                        }
                    }

                }
                Text(
                    text = buildAnnotatedString {
                        append("Reputation: ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
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
                        text = buildAnnotatedString {
                            append("Badges: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFD4AF37),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("${user.badgeCounts.gold}")
                            }
                            append(" gold, ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFC0C0C0),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("${user.badgeCounts.silver}")
                            }
                            append(" silver, ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFCD7F32),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("${user.badgeCounts.bronze}")
                            }
                            append(" bronze")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun ListLoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(32.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                ),
            )
        }
    }
}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "No users found!",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                ),
            )
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape
                    )
                    .padding(12.dp),
                painter = painterResource(id = android.R.drawable.stat_sys_warning),
                contentDescription = "No data image",
            )
        }
    }
    return

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
            ),
            followedUsers = listOf(1),
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun EmptyContentPreview() {
    StackOverflowAppTheme {
        ListContent(
            users = emptyList(),
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun LoadingPreview() {
    StackOverflowAppTheme {
        ListLoadingState()
    }
}