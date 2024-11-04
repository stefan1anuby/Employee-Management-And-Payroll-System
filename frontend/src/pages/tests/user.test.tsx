// eslint-disable-next-line import/no-extraneous-dependencies
import React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import '@testing-library/jest-dom';
import { HelmetProvider } from 'react-helmet-async';
// eslint-disable-next-line import/no-extraneous-dependencies
import { render , screen, waitFor} from '@testing-library/react';

import { CONFIG } from 'src/config-global';

import Page from '../home';

test('test expected title user page', async () => {
  render(
      <HelmetProvider>
          <Page />
      </HelmetProvider>
  );

  const title = `User - ${CONFIG.appName}`;;

  // Wait for the document title to update
  await waitFor(() => {
      expect(document.title).toBe(title);
  });
});

test('test expected profile section',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const divElement = document.querySelector("div[name='profile']");
    expect(divElement).toBeInTheDocument();
    
  });
  test('test expected email label',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const emailLabel = screen.getByLabelText("Email Address");
    expect(emailLabel).toBeInTheDocument();
    
    
  });
  test('test expected gender label',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const emailLabel = screen.getByLabelText("Gender");
    expect(emailLabel).toBeInTheDocument();
    
    
  });
  test('test expected age label',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const emailLabel = screen.getByLabelText("Age");
    expect(emailLabel).toBeInTheDocument();
    
    
  });




