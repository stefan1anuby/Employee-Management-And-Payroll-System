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
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const industries = ['Technology', 'Healthcare', 'Finance', 'Retail', 'Education'];
  const locations = ['New York', 'San Francisco', 'London', 'Tokyo', 'Berlin'];

  const handleJoinBusiness = async (): Promise<void> => {
    console.log('Entered code:', businessCode);

    if (!businessCode) {
      alert('Please enter a business code.');
      return;
    }

    try {
      const accessToken = localStorage.getItem('access_token'); // Retrieve token from local storage

      if (!accessToken) {
        alert('You must be logged in to join a business.');
        navigate('/sign-in'); // Redirect to sign-in page
        return;
      }

      const response = await fetch(`http://localhost:8080/api/businesses/${businessCode}/employees`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        console.log('Successfully joined the business.');
        const data = await response.json();
        console.log('Employee details:', data);
        navigate('/'); // Redirect to the home page or dashboard
      } else if (response.status === 404) {
        alert('Business not found. Please check the code and try again.');
      } else if (response.status === 409) {
        alert('You are already part of this business.');
      } else {
        alert('Failed to join the business. Please try again later.');
        console.error('Unexpected error:', response.status);
      }
    } catch (error) {
      console.error('Error joining the business:', error);
      alert('An error occurred. Please try again.');
    }
  };

  const validateBusinessCreationInputs = (): boolean => {
    const newErrors: { [key: string]: string } = {};

    if (!businessName.trim()) {
      newErrors.businessName = 'Business name is required.';
    }

    if (!industry) {
      newErrors.industry = 'Please select an industry.';
    }

    if (!location) {
      newErrors.location = 'Please select a location.';
    }

    setErrors(newErrors);

    // Return true if no errors, otherwise false
    return Object.keys(newErrors).length === 0;
  };

  const handleStartBusiness = async (): Promise<void> => {
    
    if (!validateBusinessCreationInputs()) {
      return;
    }
    
    const url = 'http://localhost:8080/api/businesses'; // Replace with your actual API endpoint
  
    const businessData = {
      "name": businessName,
      "address": location,
      "industry": industry,
    };

    const accessToken = localStorage.getItem("access_token");
  
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
        body: JSON.stringify(businessData),
      });
  
      if (!response.ok) {
        const error = await response.json();
        console.error('Failed to start business:', error);
        alert('Failed to start business. Please try again.');
        return;
      }
  
      const result = await response.json();
      console.log('Business started successfully:', result);
      navigate('/'); // Redirect to your desired page
    } catch (error) {
      console.error('Error starting business:', error);
      alert('An error occurred. Please try again.');
    }
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
