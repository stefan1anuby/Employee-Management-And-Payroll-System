import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import {} from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';


// ----------------------------------------------------------------------

export function OverviewAnalyticsView() {
  return (
    <DashboardContent maxWidth="xl">
      <Typography variant="h4" sx={{ mb: { xs: 3, md: 5 } }}>
        Hi, Welcome back 👋
      </Typography>


    </DashboardContent>
  );
}
