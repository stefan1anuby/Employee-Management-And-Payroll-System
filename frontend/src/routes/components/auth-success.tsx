import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

export default function AuthSuccessPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const accessToken = searchParams.get('access_token');
    const refreshToken = searchParams.get('refresh_token');

    if (accessToken && refreshToken) {
      // Store tokens in local storage or cookies
      localStorage.setItem('access_token', accessToken);
      localStorage.setItem('refresh_token', refreshToken);

      // Redirect to a dashboard or home page after successful login
      navigate('/');
    } else {
      // If tokens are missing, redirect to sign-in page
      navigate('/sign-in');
    }
  }, [searchParams, navigate]);

  return <div>Authenticating...</div>;
}