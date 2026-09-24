import Container from 'react-bootstrap/Container'
import Navbar from 'react-bootstrap/Navbar'
import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import ApplicationNavigation from './ApplicationNavigation'
import UserSessionActions from './UserSessionActions'
import { confirmAction, showError, showSuccess } from '../../utils/sweetAlert'

const ApplicationLayout = () => {
	const { user, signOut } = useAuth()
	const navigate = useNavigate()
	const navbarBackground = user?.type === 'ADMIN' ? 'danger' : 'dark'

	const handleSignOut = async () => {
		if (!(await confirmAction('Wylogować się?', 'Bieżąca sesja zostanie zakończona.'))) {
			return
		}

		try {
			await signOut()
			void showSuccess('Wylogowano')
			navigate('/login')
		} catch {
			await showError('Błąd wylogowania', 'Nie udało się zakończyć sesji.')
		}
	}

	return (
		<>
			<Navbar bg={navbarBackground} variant="dark" expand>
				<Container className="page-container">
					<Navbar.Brand as={NavLink} to="/">
						ZSZPG
					</Navbar.Brand>
					{user && <ApplicationNavigation userType={user.type} />}
					<UserSessionActions user={user} onSignOut={handleSignOut} />
				</Container>
			</Navbar>
			<Container as="main" className="page-container py-4">
				<Outlet />
			</Container>
		</>
	)
}

export default ApplicationLayout
