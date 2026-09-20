import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import Stack from 'react-bootstrap/Stack'
import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const ApplicationLayout = () => {
	const { user, signOut } = useAuth()
	const navigate = useNavigate()
	const navbarBackground = user?.type === 'ADMIN' ? 'danger' : 'dark'

	const handleSignOut = async () => {
		await signOut()
		navigate('/login')
	}

	return (
		<>
			<Navbar bg={navbarBackground} variant="dark" expand>
				<Container className="page-container">
					<Navbar.Brand as={NavLink} to="/">
						ZSZPG
					</Navbar.Brand>
					{user && (
						<Nav className="me-auto">
							<Nav.Link as={NavLink} to="/meals">
								Posiłki
							</Nav.Link>
							<Nav.Link as={NavLink} to="/categories">
								Kategorie
							</Nav.Link>
							{user.type === 'ORDERER' && (
								<Nav.Link as={NavLink} to="/cart">
									Koszyk
								</Nav.Link>
							)}
						</Nav>
					)}
					<Stack direction="horizontal" gap={3} className="text-light">
						{user ? (
							<>
								<span>
									{user.login} ({user.type})
								</span>
								<Button variant="outline-light" size="sm" onClick={handleSignOut}>
									Wyloguj
								</Button>
							</>
						) : (
							<>
								<Button
									variant="outline-light"
									size="sm"
									onClick={() => navigate('/login')}
								>
									Logowanie
								</Button>
								<Button
									variant="light"
									size="sm"
									onClick={() => navigate('/register')}
								>
									Rejestracja
								</Button>
							</>
						)}
					</Stack>
				</Container>
			</Navbar>
			<Container as="main" className="page-container py-4">
				<Outlet />
			</Container>
		</>
	)
}
export default ApplicationLayout
