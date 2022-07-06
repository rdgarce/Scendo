import React from 'react'
import {Navbar, Container, Nav} from "react-bootstrap"


function PublicNavbar() {

return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
  <Container>
  <Navbar.Brand href="../">Scendo</Navbar.Brand>
  <Navbar.Toggle aria-controls="responsive-navbar-nav" />
  <Navbar.Collapse id="responsive-navbar-nav">
  <Nav className="me-auto">
    </Nav>
    <Nav>
      <Nav.Link href='../login'>
        Login
      </Nav.Link>
      <Nav.Link href='../registrazione'>
        Registrati
      </Nav.Link>
    </Nav>
  </Navbar.Collapse>
  </Container>
</Navbar>
  )
}

export default PublicNavbar