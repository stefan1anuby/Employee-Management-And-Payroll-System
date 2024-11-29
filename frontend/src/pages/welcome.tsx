import { Helmet } from 'react-helmet-async';

import { CONFIG } from 'src/config-global';

import { WelcomeLayout } from 'src/layouts/welcome';

import { WelcomeView } from 'src/sections/welcome';

// ----------------------------------------------------------------------

export default function Page() {
  return (
    <>
      <Helmet>
        <title> {`Welcome - ${CONFIG.appName}`}</title>
      </Helmet>

      <WelcomeLayout>
        <WelcomeView/>      
      </WelcomeLayout>
    </>
  );
}
