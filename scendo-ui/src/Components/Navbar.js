import React from 'react';
import LoggedNavbar from './LoggedNavbar';
import PublicNavbar from './PublicNavbar';

const Navbar = () => {

const isAuthenticated = () =>{
    const user = localStorage.getItem('token');
    if (user)
        return true;
    else return false;
};

  return (
    <>
        {isAuthenticated() ? <LoggedNavbar/> : <PublicNavbar/>}
    </>  
  );
};

export default Navbar