interface StatusMessageProps {
	message: string | null
	variant?: 'danger' | 'success' | 'info'
}

const StatusMessage = ({ message, variant = 'danger' }: StatusMessageProps) => {
	if (!message) {
		return null
	}

	return <Alert variant={variant}>{message}</Alert>
}
export default StatusMessage
import Alert from 'react-bootstrap/Alert'
