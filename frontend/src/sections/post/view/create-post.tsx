import Card from '@mui/material/Card';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import {} from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { useState } from 'react';
import Button from '@mui/material/Button';


// ----------------------------------------------------------------------




export function CreatePost() {

    const [input, setInput] = useState('');
    
    
    const handleSubmit = async (e: { preventDefault: () => void; }) => {
      e.preventDefault();
  
      const token = localStorage.getItem('access_token'); 
      const me = JSON.parse(localStorage.getItem('me') || '{}');
      const {email} = me;
      if (!token) {
        console.error('No access token found in local storage');
        return;
      }
  
      const postInDTO = {
        text: input,
        author: email,
        timestamp: new Date().toISOString(),
      };
  
      // Encoding of the comment such that it can be sent as a resource in a get request
      const comment = postInDTO.text;
      const encodedComment = encodeURIComponent(comment);
      console.log(encodedComment);

      try {
        // Call to the Python server to see if it is a sarcastic comment
        const sarcastic = await fetch(`http://localhost:5000/sarcasm?comment=${encodedComment}`, {
          method: 'GET'
        });

        // Check if the response has 2xx as status code
        if(!sarcastic.ok && sarcastic.status !== 403) {
          throw new Error('Failed to validate the post');
        }

        // Extract the text in the response
        const text = await sarcastic.json();
        console.log('The text: ', text);

        // Set a popup window if the comment is sarcastic (in that case, the server returns 403)
        if(sarcastic.status === 403) {
          alert(text.message);
          throw new Error('Post was not validated');
        }

      } catch(error) {
        console.error('Error validating post: ', error);
        setInput('');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/api/posts', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(postInDTO)
        });
  
        if (!response.ok) {
          throw new Error('Failed to create post');
        }
  
        const postOutDTO = await response.json();
        console.log('Post created successfully:', postOutDTO);
        setInput('');
      } catch (error) {
        console.error('Error creating post:', error);
      }
    };
  
  return (
    <DashboardContent >
    <Box display="flex" alignItems="center" mb={5}>
        <Typography variant="h4" flexGrow={1}>
          Create Post
        </Typography>
        
      </Box>
      <Card sx={{ p: 3 }}>
        <Box display="flex" alignItems="center" mb={2}>
          <Box component="form" onSubmit={handleSubmit} sx={{ flexGrow: 1, ml: 2 }}>
            <TextField
              value={input}
              onChange={(e) => setInput(e.target.value)}
              type="text"
              fullWidth
              variant="outlined"
              placeholder={`What's on your mind?`}
              margin="normal"
            />
            
            <Button type="submit" variant="contained" >
              Post
            </Button>
          </Box>
        </Box>
      </Card>


    </DashboardContent>
  );
}
