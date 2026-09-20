import Stack from 'react-bootstrap/Stack'
import { Link } from 'react-router-dom'

const NotFoundPage = () => (
	<Stack className="text-center py-5">
		<h1>404</h1>
		<p>Nie znaleziono strony.</p>
		<Link to="/">Wróć do aplikacji</Link>
	</Stack>
)
export default NotFoundPage
