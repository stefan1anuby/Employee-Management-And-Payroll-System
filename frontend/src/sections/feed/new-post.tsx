import React from 'react';
import { Avatar, Box, Card, Typography } from '@mui/material';

import type { PostProps } from './view/news-feed';

type NewPostProps = {
  post: PostProps;
};

export function NewPost({ post }: NewPostProps) {
  return (
    <Card sx={{ p: 2, mb: 2 }}>
      <Box gap={2} display="flex" alignItems="center" sx={{ mb: 2 }}>
        <Avatar alt={post.author} />
        <Typography variant="subtitle1">{post.author}</Typography>
      </Box>
      <Typography variant="body1">{post.text}</Typography>
    </Card>
  );
}