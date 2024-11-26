import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

// ----------------------------------------------------------------------

export function WelcomeView(): JSX.Element {
	const navigate = useNavigate();

  const [mode, setMode] = useState<'join' | 'start' | null>(null);
  const [businessCode, setBusinessCode] = useState<string>('');
  const [businessName, setBusinessName] = useState<string>('');
  const [industry, setIndustry] = useState<string>('');
  const [location, setLocation] = useState<string>('');

  const industries = ['Technology', 'Healthcare', 'Finance', 'Retail', 'Education'];
  const locations = ['New York', 'San Francisco', 'London', 'Tokyo', 'Berlin'];

  const handleJoinBusiness = (): void => {
	console.log("Entered code:", businessCode);
	// TODO add logic here please
    const validCode = '123456789'; // Replace with your actual logic to validate the code

    if (businessCode === validCode) {
      console.log('Redirecting to home page...');
      navigate('/'); // Replace with your actual home URL
    } else {
      alert('Invalid business code. Please try again.');
    }
  };

  const handleStartBusiness = (): void => {
    console.log('Starting business:', { businessName, industry, location });
    // Add your logic here for starting a business

	navigate('/');
  };

  return (
    <>
      <Box gap={1.5} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
        <Typography variant="h5">Welcome First Timer</Typography>
        <Typography variant="body2" color="text.secondary">
          Wanna start from scratch?
          <Link
            variant="subtitle2"
            sx={{ ml: 0.5 }}
            onClick={() => setMode('start')}
            style={{ cursor: 'pointer' }}
          >
            Get started
          </Link>
        </Typography>
      </Box>

      {mode === null && (
        <>
          <Divider sx={{ m: 0, '&::before, &::after': { borderTopStyle: 'dashed' } }}>
            <Typography
              variant="overline"
              sx={{ color: 'text.secondary', fontWeight: 'fontWeightMedium' }}
            >
              OR
            </Typography>
          </Divider>
          <Box display="flex" flexDirection="column" alignItems="center">
            <Button
              variant="contained"
              color="primary"
              onClick={() => setMode('join')}
              sx={{ m: 2 }}
            >
              Enter a Code
            </Button>
          </Box>
        </>
      )}

{mode === 'join' && (
        <Box gap={2} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
          <Typography variant="h6">Join a Business</Typography>
          <TextField
            label="Enter Business Code"
            value={businessCode}
            onChange={(e) => setBusinessCode(e.target.value)}
            fullWidth
          />
          <Box display="flex" gap={2}>
            <Button variant="contained" color="primary" onClick={handleJoinBusiness}>
              Join
            </Button>
            <Button variant="outlined" color="secondary" onClick={() => setMode(null)}>
              Back
            </Button>
          </Box>
        </Box>
      )}

      {mode === 'start' && (
        <Box gap={2} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
          <Typography variant="h6">Start a New Business</Typography>
          <TextField
            label="Business Name"
            value={businessName}
            onChange={(e) => setBusinessName(e.target.value)}
            fullWidth
          />
          <FormControl fullWidth>
            <InputLabel>Industry</InputLabel>
            <Select
              value={industry}
              onChange={(e) => setIndustry(e.target.value)}
            >
              {industries.map((ind, index) => (
                <MenuItem key={index} value={ind}>
                  {ind}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl fullWidth>
            <InputLabel>Location</InputLabel>
            <Select
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            >
              {locations.map((loc, index) => (
                <MenuItem key={index} value={loc}>
                  {loc}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Box display="flex" gap={2}>
            <Button variant="contained" color="primary" onClick={handleStartBusiness}>
              Start Business
            </Button>
            <Button variant="outlined" color="secondary" onClick={() => setMode(null)}>
              Back
            </Button>
          </Box>
        </Box>
      )}
    </>
  );
}
