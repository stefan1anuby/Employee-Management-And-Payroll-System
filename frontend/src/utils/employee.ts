import { useState, useEffect } from 'react';

export const useCheckEmployeeStatus = (accessToken: string | null) => {
  const [loading, setLoading] = useState<boolean>(true);
  const [employeeExists, setEmployeeExists] = useState<boolean | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!accessToken) {
      setError('Missing access token.');
      setLoading(false);
      return;
    }

    const checkEmployee = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/employees/me', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${accessToken}`,
          },
        });

        if (response.ok) {
          setEmployeeExists(true);
        } else if (response.status === 404) {
          setEmployeeExists(false);
        } else {
          setError(`Unexpected status code: ${response.status}`);
        }
      } catch (err) {
        console.error('Error checking employee status:', err);
        setError('Failed to fetch employee status.');
      } finally {
        setLoading(false);
      }
    };

    checkEmployee();
  }, [accessToken]);

  return { loading, employeeExists, error };
};