import React from 'react'
import {Navbar, Container, Nav} from "react-bootstrap"
import AuthService from '../Services/AuthService'
import { useNavigate } from "react-router-dom";


function LoggedNavbar() {

const navigate = useNavigate()

const logout = (e) =>{
    AuthService.logout();
    navigate("/login");
    navigate(0);
}

  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
  <Container>
  <Navbar.Brand href="../">Scendo</Navbar.Brand>
  <Navbar.Toggle aria-controls="responsive-navbar-nav" />
  <Navbar.Collapse id="responsive-navbar-nav">
    <Nav className="me-auto">
      <Nav.Link href="../home/">Home</Nav.Link>
      <Nav.Link href="../crea-uscita/">Crea Uscita</Nav.Link>
    </Nav>
    <Nav>
      <Nav.Link onClick={logout}>
        Logout
      </Nav.Link>
    </Nav>
  </Navbar.Collapse>
  </Container>
</Navbar>
  )
}

export default LoggedNavbar;