// eslint-disable-next-line import/no-extraneous-dependencies
import React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import '@testing-library/jest-dom';
import { HelmetProvider } from 'react-helmet-async';
// eslint-disable-next-line import/no-extraneous-dependencies
import { render, screen , waitFor} from '@testing-library/react';

import { CONFIG } from 'src/config-global';

import Page from '../home';

test('test expected title error page', async () => {
  render(
      <HelmetProvider>
          <Page />
      </HelmetProvider>
  );

  const title = `404 page not found! | Error - ${CONFIG.appName}`;

 
  await waitFor(() => {
      expect(document.title).toBe(title);
  });
});

test('test expected error text',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const textElement = screen.getByText("Sorry, page not found!")
    expect(textElement).toBeInTheDocument();
    
  });
  