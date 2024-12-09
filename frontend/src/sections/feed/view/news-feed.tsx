import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import {} from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';
import { Box } from '@mui/material';
import { Scrollbar } from 'src/components/scrollbar';
import { useEffect, useState } from 'react';
import { NewPost } from '../new-post';



export type PostProps = {
  id: string;
  text: string;
  author: string;
};

// ----------------------------------------------------------------------




export function NewsFeed() {
  const [data, setData] = useState<PostProps[]>([]);

  const handleGet = async () => {
    const token = localStorage.getItem('access_token');
    const me = JSON.parse(localStorage.getItem('me') || '{}');
    const { businessId } = me;
    if (!token) {
      console.error('No access token found in local storage');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/businesses/${businessId}/posts`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch data');
      }

      const result = await response.json();
      setData(result);
    
    } catch (fetchError) {
      console.error('Error fetching data:', fetchError);
    }
  };

  useEffect(() => {
    handleGet();
  }, []); 

  return (
    <DashboardContent >
      <Box display="flex" alignItems="center" mb={5}>
        <Typography variant="h4" flexGrow={1}>
          News Feed
        </Typography>
      </Box>
      <Box>
        {data.map((post) => (
          <Box key={post.id} mb={2}>
            <NewPost post={post} />
          </Box>
        ))}
      </Box>
    </DashboardContent>
  );
}
