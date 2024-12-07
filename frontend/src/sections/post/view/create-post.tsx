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
    const [inputURL, setInputURL] = useState('');
    
    const handleSubmit = (e: { preventDefault: () => void; }) => {
      e.preventDefault();
      // Handle form submission logic here
      console.log('Post submitted:', { input, inputURL });
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
