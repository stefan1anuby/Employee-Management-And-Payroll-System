import { useState, useEffect } from 'react';

export const useCheckEmployeeStatus = (accessToken: string | null) => {
  const [loading, setLoading] = useState<boolean>(true);
  const [employeeExists, setEmployeeExists] = useState<boolean | null>(null);
  const [employeeData, setEmployeeData] = useState<any | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token = accessToken || localStorage.getItem("access_token"); 

    if (!token) {
      setError('Missing access token.');
      setLoading(false);
      
      return;
    }

    const checkEmployee = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/employees/me', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json(); // Parse the response body
          setEmployeeExists(true);
          setEmployeeData(data); // Save the response body
          
          // for debug purposes
          localStorage.setItem("me", JSON.stringify(data));
        } else if (response.status === 404) {
          setEmployeeExists(false);
          setEmployeeData(null);
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

  return { loading, employeeExists, employeeData, error };
};