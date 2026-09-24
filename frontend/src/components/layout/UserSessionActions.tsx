import Button from 'react-bootstrap/Button'
import Stack from 'react-bootstrap/Stack'
import { useNavigate } from 'react-router-dom'
import type { User } from '../../types'

interface UserSessionActionsProps {
	user: User | null
	onSignOut: () => Promise<void>
}

const UserSessionActions = (props: UserSessionActionsProps) => {
	const navigate = useNavigate()

	return (
		<Stack direction="horizontal" gap={3} className="text-light">
			{props.user ? (
				<>
					<span>
						{props.user.login} ({props.user.type})
					</span>
					<Button
						variant="outline-light"
						size="sm"
						onClick={() => void props.onSignOut()}
					>
						Wyloguj
					</Button>
				</>
			) : (
				<>
					<Button variant="outline-light" size="sm" onClick={() => navigate('/login')}>
						Logowanie
					</Button>
					<Button variant="light" size="sm" onClick={() => navigate('/register')}>
						Rejestracja
					</Button>
				</>
			)}
		</Stack>
	)
}

export default UserSessionActions
