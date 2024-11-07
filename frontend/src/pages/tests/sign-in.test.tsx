import '@testing-library/jest-dom';
import { MemoryRouter } from 'react-router-dom';
import { HelmetProvider } from 'react-helmet-async';
import { render, screen, waitFor } from '@testing-library/react';

import Page from '../sign-in'; 

test('test expected sign in page title', async () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

    const title = `Sign in - Minimal UI`;
    await waitFor(() => {
        expect(document.title).toBe(title);
    });
});

test('test expected sign in form',  () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

    const labelElement = screen.getByLabelText("Email address")
    
    expect(labelElement).toBeInTheDocument();
    
});
test('test expected email label',  () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

    const labelElement = screen.getByLabelText("Email address")
    
    expect(labelElement).toBeInTheDocument();
    
});
test('test expected password label',  () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

    const labelElement = screen.getByLabelText("Password");
    expect(labelElement).toBeInTheDocument();
    
});
test('test expected password hide button',  () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

   
   const toggleButton = document.querySelector("button[name='showPassword']");
   expect(toggleButton).toBeInTheDocument();
    
});
test('test expected sign in button',  () => {
    render(
        <MemoryRouter>
            <HelmetProvider>
                <Page />
            </HelmetProvider>
        </MemoryRouter>
    );

   
   const singInButton = document.querySelector("button[name='sign-in']");
   expect(singInButton).toBeInTheDocument();
    
});
