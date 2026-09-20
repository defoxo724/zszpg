import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const ApplicationLayout = () => {
	const { user, signOut } = useAuth()
	const navigate = useNavigate()
	const navbarBackgroundClass = user?.type === 'ADMIN' ? 'bg-danger' : 'bg-dark'

	const handleSignOut = async () => {
		await signOut()
		navigate('/login')
	}

	return (
		<>
			<nav className={`navbar navbar-expand navbar-dark ${navbarBackgroundClass}`}>
				<div className="container page-container">
					<NavLink className="navbar-brand" to="/">
						ZSZPG
					</NavLink>
					{user && (
						<div className="navbar-nav me-auto">
							<NavLink className="nav-link" to="/meals">
								Posiłki
							</NavLink>
							<NavLink className="nav-link" to="/categories">
								Kategorie
							</NavLink>
							<NavLink className="nav-link" to="/carts">
								Koszyki
							</NavLink>
						</div>
					)}
					<div className="d-flex align-items-center gap-3 text-light">
						{user ? (
							<>
								<span>
									{user.login} ({user.type})
								</span>
								<button
									className="btn btn-outline-light btn-sm"
									onClick={handleSignOut}
								>
									Wyloguj
								</button>
							</>
						) : (
							<>
								<NavLink className="btn btn-outline-light btn-sm" to="/login">
									Logowanie
								</NavLink>
								<NavLink className="btn btn-light btn-sm" to="/register">
									Rejestracja
								</NavLink>
							</>
						)}
					</div>
				</div>
			</nav>
			<main className="container page-container py-4">
				<Outlet />
			</main>
		</>
	)
}
export default ApplicationLayout
