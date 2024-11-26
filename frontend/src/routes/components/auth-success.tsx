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

      // TODO: to check if the user exists in one business or not
      const hasBusinessAsociated = false;
      if (hasBusinessAsociated) {
        navigate("/");
      }
      else{
        navigate("/welcome")
      }
    } else {
      // If tokens are missing, redirect to sign-in page
      navigate('/sign-in');
    }
  }, [searchParams, navigate]);

  return <div>Authenticating...</div>;
}